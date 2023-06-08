# AdConnection_dynamic_SDK_AOS Guide (V1.1.4)

### -  프로젝트 설정  (minSDK 21)


1. 제공된 AdConnection SDK AAR 파일을 app/libs 에 넣어줍니다.

<img width="322" alt="스크린샷 2023-06-07 오후 8 38 12" src="https://github.com/AdConnection/AdConnection_SDK_AOS/assets/103635743/5961da8c-2ee0-4be6-9c18-8106f9e71776">

<br/>

2. *java 프로젝트인 경우* kotlin 사용을 위해 
project 단위 build.gradle과, app 단위 build.gradle 내에 아래와 같이 코드를 추가합니다.

<br/>

**project > build.gradle**

![스크린샷 2022-04-14 오후 5 11 29](https://user-images.githubusercontent.com/103635743/163358839-7cd7825f-05a4-407b-870b-cb5a261278b3.png)

```c
classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31"
```

<br/>

**app > build.gradle**

![스크린샷 2022-04-14 오후 5 11 42](https://user-images.githubusercontent.com/103635743/163358891-39560af3-e5e7-4618-b22a-48e1b55be636.png)

```c
id 'kotlin-android'
```

<br/>

3.  app 단위 build.gradle 내에 아래와 같이 두줄을 추가합니다.

<img width="626" alt="스크린샷 2023-06-09 오전 12 31 55" src="https://github.com/AdConnection/AdConnection_SDK_AOS/assets/103635743/ca7d8910-bcd3-459d-bc51-f0236be436d3">

```c
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")
implementation files('libs/adconnection-sdk-1.1.4-release.aar')
```

<br/>

4. 광고 사용을 위한 Manifest  퍼미션 추가

```c
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="com.google.android.gms.permission.AD_ID" />
```


<br/>

*효율적인 이미지 관리를 위해 SDK에서 Glide 라이브러리를 사용합니다*

5. Glide 사용을 위해 app 단위 build.gradle에 아래와 같이 추가합니다.

**app > build.gradle**

<img width="277" alt="스크린샷 2023-06-09 오전 12 42 28" src="https://github.com/AdConnection/AdConnection_SDK_AOS/assets/103635743/f7d31226-f9ee-4971-a824-1e10326793fc">

```c
id 'kotlin-kapt'
```

<br/>

<img width="649" alt="스크린샷 2023-06-09 오전 12 45 07" src="https://github.com/AdConnection/AdConnection_SDK_AOS/assets/103635743/7ebf7e11-368a-48f6-b5c0-c6e5e521c7ed">

```c
implementation 'com.github.bumptech.glide:glide:4.13.0'
kapt "android.arch.lifecycle:compiler:1.1.1"
kapt 'com.github.bumptech.glide:compiler:4.13.0'
```

<br/><br/>



### - 광고 사용을 위한 커넥터 설정

1. 각 라이프사이클 주기마다 아래와 같이 AdConnector 를 호출해줍니다.

```c
lateinit var adConnector: AdConnector

override fun onCreate(savedInstanceState: Bundle?) {
   super.onCreate(savedInstanceState)
   setContentView(R.layout.layout_banner)

   adConnector = AdConnector(this, "발급받은 미디어키")
}

override fun onResume() {
   super.onResume()
   if(adConnector!= null) {
       adConnector.resume(this)
   }
}

override fun onPause() {
   if(adConnector!= null) {
       adConnector.pause(this)
   }
   super.onPause()
}

override fun onDestroy() {
   if(adConnector!= null) {
       adConnector.destroy(this)
   }
   super.onDestroy()
}
```

<br/>

### - 다이너믹 광고 요청

1. 띠배너 요청

```c
val listener: AdConnectorDynamicListener = object : AdConnectorDynamicListener {

    override fun onReceiveAd(v: View) {
        // 광고 수신 성공시 호출됩니다.
        Log.d("ADConnection", "[Dynamic] onReceiveAd ")
        adView = v
    }

    override fun onFailedToReceiveAd(error: String?) {
        // 광고 수신 실패시 호출됩니다.
        Log.d("ADConnection", "[Dynamic] onFailedToReceiveAd : $error")
    }

    override fun onClickAd() {
        // 광고 클릭시 호출됩니다.
        Log.d("ADConnection", "[Dynamic] onClickAd ")
    }
}

if (adConnector != null) adConnector.requestDynamicBannerView(배너소재 width, 배너소재 height, listener)
```

<br/>

2. 전면배너 요청

```c
val listener: AdConnectorDynamicListener = object : AdConnectorDynamicListener {

    override fun onReceiveAd(v: View) {
        // 광고 수신 성공시 호출됩니다.
        Log.d("ADConnection", "[Dynamic] onReceiveAd ")
        adView = v
    }

    override fun onFailedToReceiveAd(error: String?) {
        // 광고 수신 실패시 호출됩니다.
        Log.d("ADConnection", "[Dynamic] onFailedToReceiveAd : $error")
    }

    override fun onClickAd() {
        // 광고 클릭시 호출됩니다.
        Log.d("ADConnection", "[Dynamic] onClickAd ")
    }
}

if (adConnector != null) adConnector.requestDynamicIntestitialView(전면소재 width, 전면소재 height, listener)
```
