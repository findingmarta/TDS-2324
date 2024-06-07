import React, { useState } from 'react'
import { Alert, Button, Image, Pressable, SafeAreaView, StyleSheet, Switch, Text, TextInput, View } from 'react-native'
import { COLORS } from '../style/colors';
import { ScrollView } from 'react-native-gesture-handler';
import { useDispatch } from 'react-redux';
const logo = require("../images/logo.png")
import {login} from '../features/userSlice'


export default function LoginForm() {
    const [click,setClick] = useState(false);
    const [username,setUsername]=  useState("");
    const [password,setPassword]=  useState("");
    const dispatch = useDispatch();


    const handleLogin = () => {
        dispatch(login({ user: username, pass: password }))
    };
  return (
    <ScrollView> 
         <View style={styles.container}> 
         
                <Image source={logo} style={styles.image} resizeMode='contain' />

                <View style={styles.inputView}>
                    <TextInput style={styles.input} placeholder='USERNAME' value={username} onChangeText={setUsername} autoCorrect={false}
                autoCapitalize='none' />
                    <TextInput style={styles.input} placeholder='PASSWORD' secureTextEntry value={password} onChangeText={setPassword} autoCorrect={false}
                autoCapitalize='none'/>
                </View>
                
                <View style={styles.buttonView}>
                    <Pressable style={styles.button} onPress={handleLogin}>
                        <Text style={styles.buttonText}>LOGIN</Text>
                    </Pressable>
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
    borderRadius : 5,
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