from django.urls import path
from . import views

urlpatterns = [
    path('extract_table/', views.extract_table, name='extract_table'),
]
    