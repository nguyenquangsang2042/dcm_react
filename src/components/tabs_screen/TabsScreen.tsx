// screens/TabsScreen.js
import React from 'react';
import { View, Text, Button } from 'react-native';
import { useDispatch } from 'react-redux';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { logout } from "../../actions/authActions";

const TabsScreen = () => {
  const dispatch = useDispatch();

  const handleLogout = async () => {
    dispatch(logout());
    await AsyncStorage.removeItem('user');
  };

  return (
    <View>
      <Text>Tabs Screen</Text>
      <Button title="Logout" onPress={handleLogout} />
    </View>
  );
};

export default TabsScreen;
