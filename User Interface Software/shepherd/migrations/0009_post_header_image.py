# Generated by Django 3.1.4 on 2020-12-10 03:58

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('shepherd', '0008_auto_20201209_2149'),
    ]

    operations = [
        migrations.AddField(
            model_name='post',
            name='header_image',
            field=models.ImageField(blank=True, null=True, upload_to='images/'),
        ),
    ]
