# Generated by Django 3.1.4 on 2020-12-10 02:49

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('shepherd', '0007_post_snippet'),
    ]

    operations = [
        migrations.AlterField(
            model_name='post',
            name='snippet',
            field=models.CharField(max_length=255),
        ),
    ]
