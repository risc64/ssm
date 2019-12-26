package com.llf.ssm.util;
/**
  * @author llf
  * @date 2018年11月14日 下午4:10:10
  * 
  */
public class WeChatUtil {

	/**
	 * 获取微信扫码页url
	 * @param appid 应用唯一标识
	 * @param redirect_uri 授权后跳转url路径（请使用urlEncode对链接进行处理）
	 * @param response_type 填code
	 * @param scope 应用授权作用域，拥有多个作用域用逗号（,）分隔，网页应用目前仅填写snsapi_login即
	 * @param state 非必须，用于保持请求和回调的状态，可设置为简单的随机数加session进行校验
	 * @return
	 */
	public static String getLoginUrl(String appid, String redirect_uri, String response_type, String scope, String state) {
		String url = "https://open.weixin.qq.com/connect/qrconnect?appid=" + appid + 
				"&redirect_uri=" + redirect_uri + 
				"&response_type=" + response_type +
				"&scope=" + scope +
				"&state=" + state;
		return url;
	}
	
	/**
	 * 扫码授权后redirect_uri返回code，通过code获取access_token,openid
	 * @param appid 应用唯一标识
	 * @param secret 应用密钥AppSecret，在微信开放平台提交应用审核通过后获得
	 * @param code 扫码授权后跳转路径会带code，获取的code参数
	 * @param grant_type 填authorization_code
	 * @return
	 */
	public static String getAccesTokenUrl (String appid, String secret, String code, String grant_type) {
		String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + 
				"&secret=" + secret + 
				"&code=" + code + 
				"&grant_type=" + grant_type;
		return tokenUrl;	
	}
	
	/**
	 * 根据access_token,openid获取用户信息
	 * @param access_token 调用凭证
	 * @param openid 普通用户的标识，对当前开发者帐号唯一
	 * @return
	 */
	public static String getUserInfoUrl (String access_token, String openid) {
		String userMessageUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="
				+ access_token
				+ "&openid="
				+ openid;
		return userMessageUrl;
	}
	
	/**
	 * 获取公众号授权接口
	 * @param appid 应用唯一标识
	 * @param redirect_uri 授权后跳转url路径（请使用urlEncode对链接进行处理）
	 * @param response_type 填code
	 * @param scope 应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息 ）
	 * @param state 非必须，用于保持请求和回调的状态，可设置为简单的随机数加session进行校验
	 * @return
	 */
	public static String getAuthrizeUrl(String appid, String redirect_uri, String response_type, String scope, String state) {
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid +
				"&redirect_uri=" + redirect_uri + 
				"&response_type=" + response_type +
				"&scope=" + scope +
				"&state=" + state;
		return url;
	}
	
	
}
