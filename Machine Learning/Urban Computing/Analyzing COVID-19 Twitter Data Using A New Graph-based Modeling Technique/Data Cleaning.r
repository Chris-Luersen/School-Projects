arr=vector("character", length = 98) 
fileName <- "/data/Data.csv"
conn <- file(fileName,open="r")
linn <-readLines(conn)
for (i in 1:length(linn)){
   arr[i]=linn[i]
}
close(conn)
TweetEccentricity=vector("integer", length =length(arr))
Tweetclosnesscentrality=vector("integer", length =length(arr))
Tweetharmonicclosnesscentrality=vector("integer", length =length(arr))
Tweetbetweenesscentrality=vector("integer", length =length(arr))

MyData <- read.csv(file="/data/Features.csv", header=TRUE, sep=",")
tweetWords_uniq= as.vector(unlist(MyData$Id))
for (k in 1:length(arr))
{
	tweet=as.vector(unlist(arr[k]))
        tweetWords2 =as.vector(unlist(strsplit(tweet, ' ')))
	index=as.vector(unlist(match(tweetWords2,tweetWords_uniq)))
	sumEccentricity=0
        sumclosnesscentrality=0
	sumharmonicclosnesscentrality=0
	sumbetweenesscentrality=0
        NodesInTweet=0       
        for (f in 1:length(index))
	  {
             if (nchar(as.vector(unlist(tweetWords2[f])))>=3 && identical(tweetWords2[f],"ing")==FALSE)
	       {
             	sumEccentricity=sumEccentricity+MyData$Eccentricity[index[f]]
             	sumclosnesscentrality=sumclosnesscentrality+MyData$closnesscentrality[index[f]]
             	sumharmonicclosnesscentrality= sumharmonicclosnesscentrality+MyData$harmonicclosnesscentrality[index[f]]
	     	sumbetweenesscentrality= sumbetweenesscentrality+MyData$betweenesscentrality[index[f]]
                NodesInTweet=NodesInTweet+1
	        }
              else 
              {
                sumEccentricity=sumEccentricity+0
             	sumclosnesscentrality=sumclosnesscentrality+0
             	sumharmonicclosnesscentrality= sumharmonicclosnesscentrality+0
	     	sumbetweenesscentrality= sumbetweenesscentrality+0
              }
             
	  }
           TweetEccentricity[k]=sumEccentricity
           Tweetclosnesscentrality[k]=sumclosnesscentrality
           Tweetharmonicclosnesscentrality[k]=sumharmonicclosnesscentrality
	   Tweetbetweenesscentrality[k]=sumbetweenesscentrality

}
write.csv( TweetEccentricity,"/results/TweetEccentricity.csv")
write.csv( Tweetclosnesscentrality,"/results/Tweetsumclosnesscentrality.csv")
write.csv( Tweetharmonicclosnesscentrality,"/results/Tweetharmonicclosnesscentrality.csv")
write.csv( Tweetbetweenesscentrality,"/results/Tweetbetweenesscentrality.csv")
write.csv( Tweetbetweenesscentrality,"/results/Tweetbetweenesscentrality.csv")

