import React from 'react';
import { View, Text, TouchableOpacity, StyleSheet, PermissionsAndroid, Alert } from 'react-native';
import RNFS from 'react-native-fs';
import { COLORS } from '../style/colors';

const Download = ({ fileUrls }) => {
  const downloadFiles = async () => {
    for (let i=0;i<fileUrls.length;i++) {
        
      try {
        // Determine the file extension
        const split = fileUrls[i].split("/")
        const len = split.length
        const fileName = split[len-1]
        const downloadDest = `${RNFS.ExternalDirectoryPath}/${fileName}`;
        
        
        // Start the file download
        const ret = RNFS.downloadFile({
          fromUrl: fileUrls[i],
          toFile: downloadDest,
        });

        const result = await ret.promise;
        if (result.statusCode === 200) {
          Alert.alert('Success', `File downloaded to ${downloadDest}`);
        } else {
          Alert.alert('Error', `File download failed for ${fileUrls[i]}`);
        }
      } catch (error) {
        console.error(error);
        Alert.alert('Error', `An error occurred while downloading the file from ${fileUrls[i]}`);
      }
    }
  };

  return (
    <TouchableOpacity style={styles.button} onPress={downloadFiles}>
      <Text style={styles.buttonText}>Download</Text>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  button: {
    backgroundColor: COLORS.logo_yellow,
    padding: 10,
    borderRadius: 5,
    alignItems: 'center',
    margin: 10,
  },
  buttonText: {
    color: '#FFFFFF',
    fontSize: 16,
  },
});

export default Download;
