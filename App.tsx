// App.js
import React, { useState, useEffect } from 'react';
import { Provider } from 'react-redux';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { login, logout } from "./src/actions/authActions";
import store from "./src/store/configureStore";
import AppNavigator from "./src/components/navigation/AppNavigator";
import { setLoading } from "./src/actions/LoginAction";
import LoginScreen from "./src/components/login_screen/LoginScreen";
import { Text, View } from "react-native";
import LoadingScreen from "./src/components/loading_screen/LoadingScreen";

const App = () => {
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const checkLoginStatus = async () => {
      const user = await AsyncStorage.getItem('user');
      if (user) {
        const parsedUser = JSON.parse(user);
        store.dispatch(login(parsedUser));
      } else {
        store.dispatch(logout());
      }
      setIsLoading(false); // Kiểm tra xong, cập nhật trạng thái isLoading
    };
    checkLoginStatus();
  }, []);

  // Kiểm tra isLoading trước khi hiển thị giao diện chính
  if (isLoading) {
    // Hiển thị màn hình "loading" hoặc các yếu tố tương tự
    return (
      <LoadingScreen/>
    );
  }

  return (
    <Provider store={store}>
      <AppNavigator />
    </Provider>
  );
};

export default App;
