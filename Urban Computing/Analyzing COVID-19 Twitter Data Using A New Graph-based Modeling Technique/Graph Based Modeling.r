library(sqldf)
f <- function(x) {
  pr <- unlist(
    lapply(
      strsplit(x, ' '), 
      function(i) if(length(i)>1) { combn(i, 2, paste, collapse=' ')} else i
    )
  )

  tbl <- table(pr)

  d <- do.call(rbind.data.frame, strsplit(names(tbl), ' '))
  names(d) <- c('word1', 'word2')
  d$Freq <- tbl

  d
}

arr=vector("character", length = 837) 
fileName <- "/data/COVID-19_ToGraphRepresentation.csv"
conn <- file(fileName,open="r")
linn <-readLines(conn)
for (i in 1:length(linn)){
   arr[i]=linn[i]
}
close(conn)

x=f(arr)


y <- function(x) {
  pr <- unlist(
    lapply(
      strsplit(x, ' '), 
      function(i) combn(i, 2, paste, collapse=' ')
    )
  )

  tbl <- table(pr)

  d <- do.call(rbind.data.frame, strsplit(names(tbl), ' '))
  names(d) <- c('word1', 'word2')
  d
}

Wight=vector("integer", length = 837) 
for (i in 1:837) {
 z=y(arr[i])
k = sqldf("
  SELECT sum(Freq)
  FROM x d1 JOIN z d2
  ON d1.word1 = d2.word1 
  AND d1.word2 = d2.word2
  where d1.word1 <> 'ing' and d2.word1 <> 'ing' and d1.word2 <> 'ing' and d2.word2 <> 'ing' and length(d1.word1)>=3 and length(d1.word2)>=3 and length(d2.word1>=3) and length(d2.word2)>=3
")
Wight[i]=k
print(i)
next
}

word1_uniq= as.vector(unlist(unique(x$word1)))
word2_uniq= as.vector(unlist(unique(x$word2)))
tweetWords=vector("character", length =length(word1_uniq)+length(word2_uniq) )
count1=1
for (i in 1:length(word1_uniq)) { 
    tweetWords[count1]= word1_uniq[i]
    count1=count1+1
}
count2=count1
 for (j in 1:length(word2_uniq)) { 
    tweetWords[count2]= word2_uniq[j]
    count2=count2+1
}
tweetWords_uniq= as.vector(unlist(unique(tweetWords)))

WordDegree=vector("integer", length =length(tweetWords_uniq) )


count3=1
for (i in 1:length(tweetWords_uniq))
{ 
    if (nchar(tweetWords_uniq[i])>=3 && identical(tweetWords_uniq[i],"ing")==FALSE)
  	 {
	 WordDegree[count3]=length(grep(tweetWords_uniq[i], x$word1))+length(grep(tweetWords_uniq[i], x$word2))
         
		
         count3=count3+1
	}
    else 
      {
           WordDegree[count3]=0
           count3=count3+1
      }

}

#****************************Out*******************************************
count4=1
WordDegreeOut=vector("integer", length =length(word1_uniq))
for (i in 1:length(word1_uniq))
{ 
    if (nchar(word1_uniq[i])>=3 && identical(word1_uniq[i],"ing")==FALSE)
  	 {
	 WordDegreeOut[count4]=length(grep(word1_uniq[i], x$word1))	
         count4=count4+1
	}
    else 
      {
           WordDegreeOut[count4]=0
           count4=count4+1
      }

}

TweetDegreeOut=vector("integer", length =length(arr) )
for (k in 1:length(arr))
{
	tweetout=as.vector(unlist(arr[k]))
    
        tweetWords3=as.vector(unlist(strsplit(tweetout, ' ')))
       
	indexOut=as.vector(unlist(match(tweetWords3,word1_uniq)))
        indexOut[is.na(indexOut)] <- 0
        # print (indexOut)
	sumTweetDegreeOut=0
        for (f in 1:length(indexOut))
	  {
                   if (indexOut[f]!=0)
                   sumTweetDegreeOut=sumTweetDegreeOut+WordDegreeOut[indexOut[f]]
	  }
		 

           TweetDegreeOut[k]=sumTweetDegreeOut
 
}


#************************************************************************
#*****************************In******************************************
count5=1
WordDegreeIn=vector("integer", length =length(word2_uniq))
for (i in 1:length(word2_uniq))
{ 
    if (nchar(word2_uniq[i])>=3 && identical(word2_uniq[i],"ing")==FALSE)
  	 {
	 WordDegreeIn[count5]=length(grep(word2_uniq[i], x$word2))	
         count5=count5+1
	}
    else 
      {
           WordDegreeIn[count5]=0
           count5=count5+1
      }

}


TweetDegreeIn=vector("integer", length =length(arr) )
for (k in 1:length(arr))
{
	tweetIn=as.vector(unlist(arr[k]))
        tweetWords4=as.vector(unlist(strsplit(tweetIn, ' ')))
	indexIn=as.vector(unlist(match(tweetWords4,word2_uniq)))
        indexIn[is.na(indexIn)] <- 0
        # print (indexIn)
	sumTweetDegreeIn=0
        for (f in 1:length(indexIn))
	  {
               if (indexIn[f]!=0)
                    sumTweetDegreeIn=sumTweetDegreeIn+WordDegreeIn[indexIn[f]]
	  }
		 

           TweetDegreeIn[k]=sumTweetDegreeIn
 
}


#************************************************************************



NumberOfNodesInTweet=vector("integer", length =length(arr))
TweetDegree=vector("integer", length =length(arr) )
for (k in 1:length(arr))
{
	tweet=as.vector(unlist(arr[k]))
        tweetWords2 =as.vector(unlist(strsplit(tweet, ' ')))
	index=as.vector(unlist(match(tweetWords2,tweetWords_uniq)))
	sumTweetDegree=0
        for (f in 1:length(index))
	  {
             sumTweetDegree=sumTweetDegree+WordDegree[index[f]]
	  }
		 

           TweetDegree[k]=sumTweetDegree
           NumberOfNodesInTweet[k]=length(tweetWords2)
}


write.csv(x,"/results/Freq.csv")
write.csv(Wight,"/results/Wight.csv")
write.csv(TweetDegree,"/results/TweetDegree.csv")
#write.csv(WordDegree,"/results/WordDegree.csv")
#write.csv(tweetWords_uniq,"/results/tweetWords_uniq.csv")
write.csv(NumberOfNodesInTweet,"/results/NumberOfNodesInTweet.csv")
write.csv(TweetDegreeIn,"/results/TweetDegreeIn.csv")
write.csv(TweetDegreeOut,"/results/TweetDegreeOut.csv")











