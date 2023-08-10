interface ApiConfig {
  baseUrl: string;
  subSite: string;
}
const BASE_URL = "https://vnadmsportal.vuthao.com"; // DEV
//const BASE_URL = "https://vnadmsuatportal.vuthao.com"; // UAT
//const BASE_URL = "https://lib.vietnamairlines.com"; // LIVE
let config: ApiConfig = {
  baseUrl: BASE_URL,
  subSite: 'psd',
};

export const getBaseUrl = () => config.baseUrl;

export const setBaseUrl = (newBaseUrl: string) => {
  config.baseUrl = newBaseUrl;
};

export const getSubSite = () => config.subSite;

export const setSubSite = (newSubSite: string) => {
  config.subSite = newSubSite;
};

const API_CONFIG = {
  getBaseUrl,
  setBaseUrl,
  getSubSite,
  setSubSite,
};

export default API_CONFIG;
