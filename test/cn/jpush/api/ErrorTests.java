package cn.jpush.api;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 测试错误码 
 *
 */
public class ErrorTests {

	private String appKey = "57b9ef19d4be5de08df12aa0";//必填，例如466f7032ac604e02fb7bda89
	private String masterSecret = "9cc138f8dc04cbf16240daa92d8d50e2"; //必填，每个应用都对应一个masterSecret（1f0e3dad99908345f7439f8ffabdffc4)
	private  JPushClient jpush = null;
	private int sendNo = 11111;
	/*
	 * 保存离线的时长。秒为单位。最多支持10天（864000秒）。
	 * 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。
	 * 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒）。	
	 */
	private static int timeToLive =  60 * 60 * 24;  

	public ErrorTests(){
		jpush = new JPushClient(masterSecret, appKey);
		//jpush = new JPushClient(masterSecret, appKey,timeToLive);
	}
	
	/*
	 * 参数值不合法 1003
	 */
	@Test
	public void testSendNotificationWithAppKeyInvalidParameter(){
		String appKey = "9cc138f8dc04cbf16240daa92d8d50e21";
		jpush = new JPushClient(masterSecret, appKey);


		int erroCode = ErrorCodeEnum.InvalidParameter.value();
		String msgTitle = "jpush";
		String msgContent = "junit-jpush-invalidParam";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}
	
	/*
	 * 消息体太大 1005
	 */
	@Test
	public void testSendNotificationWithAppKeyBigMessage(){
		int erroCode = ErrorCodeEnum.DataTooBig.value();
		String msgTitle = "jpush";
		String msgContent = "jpush jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj" +
		"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
		"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
		"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
		"sddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddeeeeeeeeeeeee" +
		"sdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddeeeeeeeeeee" +
		"sdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddcontenteeeeeeeeeeee";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}


	
    /*
     * 1004 verification_code 验证失败
     */

	@Test
	public void testSendNotificationWithAppKeyValidateFailed (){
		String masterSecret = "9cc138f8dc04cbf16240daa92d8d50e21";
		jpush = new JPushClient(masterSecret, appKey);

		int erroCode = ErrorCodeEnum.ValidateFailed.value();
		String msgTitle = "jpush";
		String msgContent = "jpush";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}


	/*
	 * 1007 IMEI不合法   验证失败
	 */
	@Test
	public void testSendNotificationWithAppKeyInvalidIMEI(){

		int erroCode = ErrorCodeEnum.InvalidIMEI.value();
		String msgTitle = "jpush";
		String msgContent = "jpush";
		String imei = "37492423";//不存在的IMEI

		MessageResult result = jpush.sendNotificationWithImei(sendNo, imei, msgTitle, msgContent);
		System.err.println(result);
		assertEquals(erroCode, result.getErrcode());

	}
	
	/*
	 * 1011  tag，algin、imei不存在  验证失败
	 */
	@Test
	public void testSendNotificationWithAppKeyInvalidPush(){
		
		int erroCode = ErrorCodeEnum.InvalidPush.value();
		String msgTitle = "jpush";
		String msgContent = "jpush";
		String tag = "and 1=1";//不存在tag

		MessageResult result = jpush.sendNotificationWithTag(sendNo, tag, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());

	}
	
	
	/*
	 * 1008  appkey不存在  验证失败
	 */
	@Test
	public void testSendNotificationWithAppKeyInvalidAppKey(){
		String appkey = "57b9ef19d4be5de08df12ac1";
		jpush = new JPushClient(appkey, appkey);
		
		int erroCode = ErrorCodeEnum.InvalidAppKey.value();
		String msgTitle = "jpush1";
		String msgContent = "jpush1";
		
		MessageResult result = jpush.sendNotificationWithAppKey(erroCode, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());

	}


	
	
	/*
	 * 特殊字符测试
	 */
	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter(){
		
		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "+j|u@n!i~t-j#p$u%s^h&-i*n(v)a_l=i?d>P<a,r.a;m；a[b]{c}";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}

}


