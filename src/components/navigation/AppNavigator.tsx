// navigation/AppNavigator.js
import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { useSelector } from 'react-redux';
import TabsScreen from '../tabs_screen/TabsScreen';
import LoginScreen from '../login_screen/LoginScreen';

const Stack = createNativeStackNavigator();

const AppNavigator = () => {
  const isLoggedIn = useSelector((state) => {
    return state["auth"].isLoggedIn;
  });

  return (
    <NavigationContainer>
      <Stack.Navigator>
        {isLoggedIn ? (
          <Stack.Screen options={{ headerShown: false }} name="TabsScreen" component={TabsScreen} />
        ) : (
          <Stack.Screen options={{ headerShown: false }} name="LoginScreen" component={LoginScreen} />
        )}
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default AppNavigator;
