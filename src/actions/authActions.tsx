import ApiUtils from "../utils/ApiUtils";
import API_CONFIG from "../config/ApiConfig";

export const login = (user: { username: string; password: string; }) => {
  ApiUtils.postPostData(API_CONFIG.getSubSite()+'/api/ApiMobile.ashx?func=AdfsLogin',user);
  ApiUtils.fetchGetData(API_CONFIG.getSubSite()+'/api/ApiMobile.ashx?func=CurrentUser');
  return {
    type: user.password==''|| user.username==''?'LOGOUT':'LOGIN_SUCCESS',
    payload: user.password==''|| user.username==''?null:user,
  };
};

export const logout = () => {
  return {
    type: 'LOGOUT',
  };
};
