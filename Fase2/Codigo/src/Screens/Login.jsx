import React, { useEffect,useState } from 'react'
import { Alert, Image, StyleSheet, Text, TextInput, View, TouchableOpacity } from 'react-native'
import { COLORS } from '../style/colors';
import { ScrollView } from 'react-native-gesture-handler';
import { useDispatch } from 'react-redux';
import {login} from '../features/userSlice'
import { useNavigation } from '@react-navigation/native';

export default function LoginForm() {
  const dispatch = useDispatch();
  const navigation = useNavigation();

  const [username,setUsername]=  useState("");
  const [password,setPassword]=  useState("");
  const [errors, setErrors] = useState({});
  const [isFormValid, setIsFormValid] = useState(false);

  useEffect(() => { 
  
    // Trigger form validation when name,  
    // email, or password changes 
    validateForm(); 
  }, [username, password]);

  const handleLogin = () => {
    if (isFormValid) {
      dispatch(login({ user: username, pass: password }))
        .then((action) => {
          if (login.fulfilled.match(action)) {
            // Handle success
            navigation.navigate('home');
          } else {
            // Handle failure
            Alert.alert('Login failed', 'Invalid username or password', [{ text: 'OK' }]);
          }
        })
        .catch((error) => {
          // Handle unexpected errors
          console.error('Unexpected error:', error);
        });
    }
  };

  const validateForm = () => { 
    let errors = {}; 

    // Validate usernamename field 
    if (!username) { 
        errors.username = 'Username is required.'; 
    } 

    // Validate password field 
    if (!password) { 
        errors.password = 'Password is required.'; 
    }

    // Set the errors and update form validity 
    setErrors(errors); 
    setIsFormValid(Object.keys(errors).length === 0); 
  };

  return (
    <ScrollView> 
      <View style={styles.container}> 
      
            <Image source={require("../images/logo.png")} style={styles.image} resizeMode='contain' />

            <View style={styles.inputView}>
                <TextInput style={styles.input} placeholder='USERNAME' value={username} onChangeText={setUsername} autoCorrect={false} autoCapitalize='none' />
                <TextInput style={styles.input} placeholder='PASSWORD' value={password} onChangeText={setPassword} autoCorrect={false} autoCapitalize='none' secureTextEntry/>
            </View>
            
            <View style={styles.buttonView}>
                <TouchableOpacity style={[styles.button, { opacity: isFormValid ? 1 : 0.5 }]} disabled={!isFormValid} onPress={handleLogin}>
                  <Text style={styles.buttonText}>LOGIN</Text>
                </TouchableOpacity>
            </View>
      </View>
    </ScrollView>
  )
}


const styles = StyleSheet.create({
  container : {
    alignItems : "center",
    paddingTop: 70,
  },

  image : {
    height : 300,
    width : 300
  },

  inputView : {
    gap : 15,
    width : "90%",
    paddingHorizontal : 40,
    marginBottom  :5
  },

  input : {
    height : 50,
    paddingHorizontal : 20,
    borderColor : "gray",
    borderWidth : 1,
    borderRadius: 7
  },

  button : {
    backgroundColor : COLORS.logo_blue,
    height : 45,
    marginTop: 40,
    borderColor : "gray",
    borderWidth  : 1,
    borderRadius : 10,
    alignItems : "center",
    justifyContent : "center"
  },

  buttonText : {
    color : "white"  ,
    fontSize: 18,
    fontWeight : "bold"
  }, 

  buttonView :{
    width :"90%",
    paddingHorizontal : 50
  },
  
})