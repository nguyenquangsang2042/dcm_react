import ApiUtils from "../utils/ApiUtils";
import API_CONFIG from "../config/ApiConfig";
import { encrypt } from "../utils/CryptUtils";

export const login = async (user: { username: string; password: string; }) => {
  let data = await encrypt(user);
  console.log(data);
  ApiUtils.postPostData(API_CONFIG.getSubSite() + '/api/ApiMobile.ashx?func=AdfsLogin', data);
  return {
    type: user.password == '' || user.username == '' ? 'LOGOUT' : 'LOGIN_SUCCESS',
    payload: user.password == '' || user.username == '' ? null : user,
  };
};

export const logout = () => {
  return {
    type: 'LOGOUT',
  };
};
