import axios from 'axios';
import API_CONFIG, { getBaseUrl } from "../config/ApiConfig";
import ApiConfig from "../config/ApiConfig";


export const fetchGetData = async (endPoint:String) => {
  try {
    console.log(`url:${API_CONFIG.getBaseUrl()}/${endPoint}`);
    const response = await axios.get(`${API_CONFIG.getBaseUrl()}/${endPoint}`);
    console.log(response.headers);
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error('Error fetching data:', error);
    throw error;
  }
};

export const postPostData = async (endPoint:String,data: any) => {
  try {
    console.log(`url:${API_CONFIG.getBaseUrl()}/${endPoint}`)
    console.log(data);
    const response = await axios.post(`${API_CONFIG.getBaseUrl()}/${endPoint}`, data);
    console.log(response.headers);
    console.log(response.data);

    return response.data;
  } catch (error) {
    console.error('Error posting data:', error);
    throw error;
  }
};
const ApiUtils={
  fetchGetData,
  postPostData
};
export default ApiUtils;
