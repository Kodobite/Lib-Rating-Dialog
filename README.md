# Lib-Rating-Dialog

##### Dependency

```java
	        implementation 'com.github.Kodobite:Lib-Rating-Dialog:0.2'
```
##### onclick Listener

```java
RatingUtils ratingUtils = new RatingUtils(this);
            ratingUtils.setDialog();
            ratingUtils.showDialog();
```

##### launch on count

```java
 RatingUtils ratingUtils = new RatingUtils(this);
        ratingUtils.setTargetLaunchCount(6);
        ratingUtils.startLaunchCounting(true);
        ratingUtils.setDialog();
