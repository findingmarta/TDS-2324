import React from 'react';
import { Provider } from 'react-redux'
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';

import store from './src/app/store';
import BottomNavigation from './src/menu/bottomNavigation';

const loginStack = createStackNavigator()



function App() {

  return (
    <Provider store={store}>
      <NavigationContainer>
        <loginStack.Navigator>
          <loginStack.Screen name="home" component={BottomNavigation} options={{ headerShown: false }} />
        </loginStack.Navigator>
      </NavigationContainer>
    </Provider>
  );
}

export default App;
