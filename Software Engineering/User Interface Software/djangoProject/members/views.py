from django.contrib.auth.views import PasswordChangeView
from django.shortcuts import render, get_object_or_404
from django.urls import reverse_lazy
from django.views import generic
from django.views.generic import DetailView, CreateView

from shepherd.models import Profile
from .forms import SignUpForm, PasswordChangingForm, EditSettingsForm, ProfilePageForm


class CreateProfilePageView(CreateView):
    model = Profile
    form_class = ProfilePageForm
    template_name = 'registration/create_user_profile_page.html'

    # fields = '__all__'

    def form_valid(self, form):
        form.instance.user = self.request.user
        return super().form_valid(form)


class EditProfilePageView(generic.UpdateView):  # dont forget to add the view to urls.py!
    # form_class = EditProfileForm
    model = Profile  # what in models.py do you want to work on?
    template_name = 'registration/edit_profile_page.html'  # what html template do you want to use?
    fields = ['profile_pic', 'website_url', 'facebook_url', 'instagram_url', 'pinterest_url', 'bio']
    success_url = reverse_lazy('shepherd:home')


class ShowProfilePageView(DetailView):
    model = Profile
    template_name = 'registration/user_profile.html'

    # noinspection PyArgumentList0
    def get_context_data(self, *args, **kwargs):
        # users = Profile.objects.all()
        context = super(ShowProfilePageView, self).get_context_data(*args, **kwargs)

        page_user = get_object_or_404(Profile, id=self.kwargs['pk'])  # getting pk from self.kwargs

        context['page_user'] = page_user
        return context


class PasswordsChangeView(PasswordChangeView):
    form_class = PasswordChangingForm
    # form_class = PasswordChangeForm
    success_url = reverse_lazy('members:password_success')
    # success_url = reverse_lazy('shepherd:home')


def password_success(request):
    return render(request, "registration/password_success.html", {})


class UserRegisterView(generic.CreateView):
    form_class = SignUpForm
    template_name = 'registration/register.html'
    success_url = reverse_lazy('login')


class UserEditView(generic.UpdateView):
    model = Profile
    form_class = EditSettingsForm
    template_name = 'registration/edit_profile.html'
    success_url = reverse_lazy('shepherd:home')

    def get_object(self, **kwargs):
        return self.request.user
