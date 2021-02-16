from django.urls import path

from .views import HomeView, ArticleDetailView, AddPostView, UpdatePostView, DeletePostView, AddCategoryView, \
    CategoryView, CategoryListView, LikeView, AddCommentView

app_name = 'shepherd'
urlpatterns = [
    # as_view to use the class HomeView from views.py
    path('', HomeView.as_view(), name="home"),
    # first quotes = path for url title, pk = primary key for each post
    path('story/<int:pk>', ArticleDetailView.as_view(), name="article_detail"),
    path('add_post/', AddPostView.as_view(), name="add_post"),
    path('add_category/', AddCategoryView.as_view(), name="add_category"),
    path('story/edit/<int:pk>', UpdatePostView.as_view(), name="update_post"),
    path('story/<int:pk>/remove', DeletePostView.as_view(), name="delete_post"),
    path('category/<str:cats>', CategoryView, name="category"),
    path('category-list/', CategoryListView, name="category-list"),
    path('like/<int:pk>', LikeView, name="like_post"),
    path('article/<int:pk>/add_comment/', AddCommentView.as_view(), name="add_comment"),
    # path('like_post/<int:pk>', LikePostView, name="like_post"), # Ajax like function
]
