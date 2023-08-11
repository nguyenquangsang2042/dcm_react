// screens/LoginScreen.js
import React, { useState } from "react";
import { View, Text, TextInput, Button, StyleSheet, ImageBackground, Pressable, Image, Alert } from "react-native";
import { useDispatch, useSelector } from "react-redux";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { login } from "../../actions/authActions";
import LoadingScreen from "../loading_screen/LoadingScreen";
import { setLoading } from "../../actions/LoginAction";
import AlertHelper from "../../utils/AlertHelper";
import ApiUtils from "../../utils/ApiUtils";
import API_CONFIG from "../../config/ApiConfig";

const LoginScreen = () => {
  const [username, setUsername] = useState("vnadmsuat.adminsite");
  const [password, setPassword] = useState("vnadmsuat@123$%");
  const dispatch = useDispatch();
  const loading = useSelector(state => state["loading"]);
  const handleLogin = async () => {
    dispatch(setLoading(true));
    setTimeout(async () => {
      const user = { username, password };
      const data = dispatch(await login(user)).payload;
      if (data != null) {
        await AsyncStorage.setItem("user", JSON.stringify(user));
      } else {
        AlertHelper.showAlertOnlyCancel("Thông báo", "Vui lòng kiêm tra thông tin đăng nhập", { text: "Đóng" });
      }
      dispatch(setLoading(false));
    }, 2000);
  };
  return (
    <ImageBackground style={styles.imageBackground} source={require("../../../assets/images/background_main.png")}>
      {
        loading ? (<LoadingScreen />) : (
          <>
            <View style={styles.container}>
              <View style={styles.inputContainer}>
                <View style={styles.containerIcon}>
                  <Image source={require("../../../assets/images/icon_username.png")} style={styles.icon} />
                </View>
                <TextInput
                  style={styles.input}
                  placeholder="Username"
                  value={username}
                  onChangeText={(text) => setUsername(text)}
                />
                <View style={styles.containerIcon}>
                  <Image source={require("../../../assets/images/icon_username.png")} style={styles.icon} />
                </View>
              </View>
            </View>
            <View style={styles.container}>
              <View style={styles.inputContainer}>
                <View style={styles.containerIcon}>
                  <Image source={require("../../../assets/images/icon_username.png")} style={styles.icon} />
                </View>
                <TextInput
                  style={styles.input}
                  placeholder="Password"
                  value={password}
                  onChangeText={(text) => setPassword(text)}
                  secureTextEntry
                />
                <View style={styles.containerIcon}>
                  <Image source={require("../../../assets/images/icon_username.png")} style={styles.icon} />
                </View>
              </View>
            </View>
            <Pressable style={styles.buttonLogin} onPress={handleLogin}><Text
              style={styles.textButtonLogin}>Đăng nhập</Text></Pressable>
          </>)
      }
    </ImageBackground>
  );
};
const styles = StyleSheet.create(
  {
    containerIcon: {
      justifyContent: "center"
    },
    inputContainer: {
      marginLeft: 20,
      marginRight: 20,
      marginBottom: 10,
      padding: 5,
      flexDirection: "row",
      borderColor: "#ccc",
      borderWidth: 1,
      borderRadius: 5
    },
    icon: {
      width: 20,
      height: 20
    },
    container: {
      justifyContent: "center",
      alignItems: "center",
      paddingLeft: 20,
      paddingRight: 20,
      paddingBottom:5
    },
    input: {
      width: "100%",
      height: 40
    },
    buttonLogin: {
      width:'90%',
      backgroundColor: "#DBA410",
      alignItems: "center",
      justifyContent: "center",
      borderRadius: 6,
      height: 40
    },
    textButtonLogin: {
      color: "white"
    },
    imageBackground: {
      width: "100%",
      height: "100%",
      justifyContent: "center",
      alignItems: "center"
    }
  }
);
export default LoginScreen;
