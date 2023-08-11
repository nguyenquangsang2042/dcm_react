import ApiUtils from "../utils/ApiUtils";
import API_CONFIG from "../config/ApiConfig";

export const login = async (user: { username: string; password: string; }) => {
  ApiUtils.postPostData(API_CONFIG.getSubSite() + '/api/ApiMobile.ashx?func=AdfsLogin', user);
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
