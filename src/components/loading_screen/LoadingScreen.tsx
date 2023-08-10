import { ImageBackground } from "react-native";
import React from "react";

const LoadingScreen=()=>{
  return(
    <ImageBackground style={{width:'100%',height:'100%'}} source={require("../../../assets/images/background_main.png")}/>
  );
}
export default LoadingScreen;
