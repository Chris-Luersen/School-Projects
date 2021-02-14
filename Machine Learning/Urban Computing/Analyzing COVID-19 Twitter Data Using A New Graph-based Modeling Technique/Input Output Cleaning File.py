# -*- coding: utf-8 -*-

import sys
import os
import re
import string
import nltk
import io
nltk.download('stopwords')
nltk.download('punkt')

def clean(inputDir, outputFile):

    # print("Cleaning "+path)
    from nltk.corpus import stopwords
    from nltk.stem import PorterStemmer
    from nltk.tokenize import sent_tokenize, word_tokenize
    ps = PorterStemmer()
    #stop_words = set(stopwords.words('english'))
    stop_words = nltk.corpus.stopwords.words('english')
   # stop_words.append('heart')
    #stop_words.append('attack')
    #print(stop_words)
    #stop_words.append('heart attack')
    WRITE_HANDLER = open(outputFile, 'w')
    tweets = dict()
    for line in open(inputDir + '/Covid-19_1027_ToCleaning.txt','rb'):
        #print(line)
        line= line.decode('ISO-8859-1')
        line = re.sub(r'[.,"!*;]+', '', line, flags=re.MULTILINE)  # removes the characters specified
        line = re.sub('&lt;', '', line, flags=re.MULTILINE)  # removes &lt;
        line = re.sub('RT', '', line, flags=re.MULTILINE)  # removes RT
        line = re.sub('&gt;', '', line, flags=re.MULTILINE)  # removes &gt
        line = re.sub('OMG', 'oh my god', line, flags=re.MULTILINE)  # repair OMG to oh my god
        line = re.sub('omg', 'oh my god', line, flags=re.MULTILINE)  # repair omg to oh my god
        line = re.sub('-__-', '', line, flags=re.MULTILINE)  # remove -__-
        line = re.sub('Omgggg', 'oh my god', line, flags=re.MULTILINE)  # repair Omgggg to oh my god
        line = re.sub(r'https?:\/\/.*[\r\n]*', '', line, flags=re.MULTILINE)  # remove link
        line = re.sub(r'[:]+', '', line, flags=re.MULTILINE)
        
        line = list(filter(lambda x: x in string.printable, line)) # filter non-ascii characers
        new_line = ''
        line = ''.join(line)
        line=' '.join(re.sub("(@[A-Za-z0-9]+)|([^0-9A-Za-z \t])|(\w+:\/\/\S+)"," ",line).split())
        
        for i in line.split():  # remove @ and #words, punctuataion
            if not i.startswith('@') and not i.startswith('#') and i not in string.punctuation:
                new_line += i + ' '
        line = new_line
        
       
        # # Do sentence correction
        Tline=''
        words =line.split()
        #print(stopwords)
        for r in words:
          r=r.lower()
          if not r in stop_words:
            Tline=Tline+' '+r
            #print(Tline)
        new_line = Tline
        #print(new_line)
        new_line = word_tokenize(new_line)
        sline=''
        for w in new_line:
            sline=sline+' '+ps.stem(w)
            #print(w + ":" + ps.stem(w))
        #print(sline)    
        WRITE_HANDLER.write(sline + '\n')
    return outputFile

DATA_FOLDER ='/data/'
CLEANED_DATA = '/results/Covid-19_1027_Cleaned.txt'
clean(DATA_FOLDER, CLEANED_DATA)