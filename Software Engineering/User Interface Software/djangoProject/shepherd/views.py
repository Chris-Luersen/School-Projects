import requests
from azure.ai.textanalytics import TextAnalyticsClient
from azure.core.credentials import AzureKeyCredential
from django.http import HttpResponseRedirect
from django.shortcuts import render, get_object_or_404
from django.urls import reverse_lazy, reverse
from django.views.generic import ListView, DetailView, CreateView, UpdateView, DeleteView

from shepherd.models import Post, Category, Comment
from .forms import PostForm, EditForm, CommentForm


# noinspection PyUnusedLocal
def LikeView(request, pk):
    post = get_object_or_404(Post, id=request.POST.get('post_id'))
    liked = False
    if post.likes.filter(id=request.user.id).exists():
        post.likes.remove(request.user)
        liked = False
    else:
        post.likes.add(request.user)
        liked = True
    return HttpResponseRedirect(reverse('shepherd:article_detail', args=[str(pk)]))


class HomeView(ListView):
    model = Post
    template_name = 'stories/home.html'
    ordering = ['-post_date']

    # ordering = ['-id']

    def get_context_data(self, *args, **kwargs):
        cat_menu = Category.objects.all()
        context = super(HomeView, self).get_context_data(*args, **kwargs)
        context['cat_menu'] = cat_menu
        return context


def CategoryListView(request):
    cat_menu_list = Category.objects.all()

    endpoint = "https://en.wikipedia.org/w/api.php"
    parameters = {
        'action': 'query',
        'list': 'search',
        'srsearch': 'Category.name',
        'format': 'json',
    }
    response = requests.get(endpoint, params=parameters)
    wikidata = response.json()
    top_wiki_title = wikidata['query']['search'][0]['title']

    return render(request, 'stories/category_list.html',
                  {'cat_menu_list': cat_menu_list, "top_wiki_title": top_wiki_title})


def CategoryView(request, cats):
    category_posts = Post.objects.filter(
        category=cats.replace('-', ' '))  # query category var in Post model for cats str from urls
    return render(request, 'stories/categories.html',
                  {'cats': cats.title().replace('-', ' '), 'category_posts': category_posts})


class ArticleDetailView(DetailView):
    model = Post
    template_name = 'stories/article_details.html'

    # example for adding category dropdown menu to articles page
    def get_context_data(self, *args, **kwargs):
        cat_menu = Category.objects.all()
        context = super(ArticleDetailView, self).get_context_data(**kwargs)

        stuff = get_object_or_404(Post, id=self.kwargs['pk'])
        total_likes = stuff.total_likes()

        liked = False
        if stuff.likes.filter(id=self.request.user.id).exists():
            liked = True

        context['cat_menu'] = cat_menu
        context['total_likes'] = total_likes
        context['liked'] = liked
        return context


# create view and add fields from model
class AddPostView(CreateView):
    model = Post
    form_class = PostForm
    template_name = 'stories/add_post.html'
    # fields = '__all__'
    # fields = ('title','body')


class AddCommentView(CreateView):
    model = Comment
    form_class = CommentForm
    # fields = '__all__'
    template_name = 'stories/add_comment.html'

    def authenticate_client(CreateView):
        key = "13bc24274d694152a366f9868a1875da"
        endpoint = "https://text-analytics5774.cognitiveservices.azure.com/"
        ta_credential = AzureKeyCredential(key)
        text_analytics_client = TextAnalyticsClient(
            endpoint=endpoint,
            credential=ta_credential)
        return text_analytics_client

    client = authenticate_client(CreateView)

    def language_detection_example(client):
        try:
            documents = ["Post.body"]
            response = client.detect_language(documents=documents, country_hint='us')[0]
            print("Language: ", response.primary_language.name)

        except Exception as err:
            print("Encountered exception. {}".format(err))

    language_detection_example(client)

    def form_valid(self, form):
        form.instance.post_id = self.kwargs['pk']  # pk is passed in as a kwarg
        return super().form_valid(form)

    success_url = reverse_lazy('shepherd:home')


class AddCategoryView(CreateView):
    model = Category
    # form_class = PostForm
    template_name = 'stories/add_category.html'
    fields = '__all__'


class UpdatePostView(UpdateView):
    model = Post
    form_class = EditForm
    template_name = 'stories/update_post.html'
    # fields = ('title', 'title_tag', 'body')


class DeletePostView(DeleteView):
    model = Post
    template_name = 'stories/delete_post.html'
    success_url = reverse_lazy('shepherd:home')

# def LikePostView(request, pk):
#     is_ajax = request.headers.get('x-requested-with') == 'XMLHttpRequest'
#     if is_ajax and request.method == 'POST':
#         pk = request.POST.get('story_id')  # get story_id from ajax script
#         try:
#             story = Post.objects.get(pk=pk)
#             story.likes = story.likes + 1
#             story.save()
#             return JsonResponse({'success': 'success', 'likes': story.likes}, status=200)
#         except Post.DoesNotExist:
#             return JsonResponse({'error': 'No story found with that ID.'}, status=200)
#     else:
#         return JsonResponse({'error': 'Invalid Ajax request.'}, status=400)
