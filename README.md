VEGA 지문인식 SDK
==============

지문인식 SDK는 지문인식을 지원하는 단말에서 활용할 수 있는 API를 지원합니다.
저장된 지문만을 활용하며 직접 저장하여 확인하는것은 현재 불가 합니다.

### SDK 지원환경

대상 모델 : 지문인식 기능이 탑재된 VEGA 모델
* VEGA LTE A (IM-A880S)
* VEGA Secret Note (IM-A890S / IM-A890K / IM-A890L)
* VEGA Secret Up (IM-A900S / IM-A900K / IM-A900L)

대상 모델 S/W 버전
* Android JB 기준으로 개발 되었으며 위 모델에서 JellyBean 버전만을 지원합니다. 
* 추후 단말 OS upgrade 시 SDK도 upgrade 예정입니다.

> * 본 가이드에서 제공하는 SDK는 지문인증을 통한 lock해재와 같은 기능이 필요로 하는 개발자를 위해서 배포되었으며, 전자금융 및 본인인증을 필요로 하는 사업자의 경우, 팬택의 보안모듈 파트너사인 크루셜소프트의 보안Solution과 연동이 필요함.

SDK 개발환경 설정
------------
[Android 기본 개발환경 설정 및 SDK 설치] (https://docs.google.com/document/d/1-VbIo_Bh5JpGWfXwZUunf3sG63w6Nooz-4QXdR6WHbg) 문서를 활용하여 개발환경 설정을 하시기 바랍니다.


단말 상태 확인
-------------
    
### 사용 예

> * 단말 상태는 FingerScanUtil class를 활용하여 확인할 수 있습니다.
> * 모두 static method로 접근이 가능합니다.

```java
// initialize all states of this device...
boolean isEnrolled = false;
boolean supportFinger = false;
boolean hasPermission = false;
		
// 1. check this device supports fingerscan SDK
supportFinger = FingerScanUtils.isDeviceSupportFingerScan(getApplicationContext());
if(supportFinger){
	// 2. check this application has a permission for fingerscan 
	hasPermission = FingerScanUtils.hasFingerScanPermission(getApplicationContext());;
	// 3. check a user registers fingerprint
	isEnrolled = FingerScanUtils.isEnrolled(getApplicationContext());
}
```

지문 인식 화면 호출 
-------------

### 사용 예

> * 단말에 구현된 화면을 활용하여 지문인식 결과를 받습니다.
> * Activity 또는 Fragment에서만 사용 가능하며 FingerScanActivity 또는 FingerScanFragment를 상속받아야 합니다.


```java
public class SampleDeviceDefaultActivity extends FingerScanActivity implements OnVerifyListener{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			requestVerifyActivity(this);
		} catch (IllegalAccessException e) {
			// this exception occur if this device does not support finger scan
			e.printStackTrace();
		} catch (SecurityException e) {
			// this exception occur if this device declare permission of finger scan
			e.printStackTrace();
		}
	}

	@Override
	public void onVerified(boolean isSuccess) {
		//get verified result here
	}
}
```

지문 인식 직접 구현
-------------
    
### 사용 예

> * 직접 FingerScanHelper class를 활용하여 background 에서도 지문 인식을 요청합니다.
> * requestVerify 한 뒤 인증이 완료 되면 stopRequestVerify을 호출 해야합니다.
> * 유저의 응답이 없이 일정 시간이 되면 자동으로 stopRequestVerify가 호출됩니다.

```java

private FingerScanHelper mFingerScanHelper;

public void startRequest(){	
	try {
		//create instance of fingerscan helper
		mFingerScanHelper = new FingerScanHelper(getApplicationContext());			
		mFingerScanHelper.requestVerify(this);
	} catch (IllegalAccessException e) {
		// this exception occur if this device does not support finger scan			
		e.printStackTrace();
	} catch (SecurityException e) {
		// this exception occur if this device declare permission of finger scan			
		e.printStackTrace();
	}
}
public void stopRequest(){
	mFingerScanHelper.stopRequestVerify();
}

@Override
public void onVerified(boolean isSuccess) {	
	//get verified result here
}
```

