TwinsChain Implementation
=========================

Building 
--------

The following instruction are for Linux environment only.

1. First, you need to install Java. Oracle Java 8 is preferred. For 
Ubuntu, you can follow this article 
http://tecadmin.net/install-oracle-java-8-jdk-8-ubuntu-via-ppa/, for 
other distribution, download from the Oracle website and install manually.
   
2. Scala Build Tool (SBT) is needed. Follow download section at the 
website to install http://www.scala-sbt.org/download.html
      
3. We use snapshot versions for Scorex and IODB. Please build and publish them locally 
      because of that
      
IODB:

    git clone https://github.com/input-output-hk/iodb.git
    sbt publishLocal

Scorex:

    git clone https://github.com/ScorexFoundation/Scorex.git
    sbt publishLocal
    
4. Build and run TwinsChain
 
    
    git clone https://twinscoin@bitbucket.org/twinscoin/twinschain.git
    sbt compile
    sbt run
                
and choose whether to run a node (`HybridApp`) or to run a simulation of 
two chains competition (`PrivateChain`)                
 
to change setting of a node running, check settings1.json default -
settings10.json in src/main/resources               