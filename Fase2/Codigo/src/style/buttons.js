import React from 'react';
import { Text, StyleSheet, Pressable } from 'react-native';
import PropTypes from 'prop-types';
import { COLORS } from './colors';

export default function Button(props) {
    const { onPress, title } = props;
    return (
      <Pressable style={styles.button} onPress={onPress}>
        <Text style={styles.text}>{title}</Text>
      </Pressable>
    );
  }
  
  Button.propTypes = {
    onPress: PropTypes.func.isRequired,
    title: PropTypes.string,
  };

export const small = {
    alignItems: 'center',
    justifyContent: 'center',
    paddingHorizontal: 15,
    paddingVertical: 15,
    width: 175
  };
  
  export const rounded = {
    borderRadius: 10
  };

  export const colors = {
    backgroundColor: COLORS.logo_yellow,
    color: COLORS.white
  };
  
  export const smallRoundedBlue = {
    ...colors,
    ...small,
    ...rounded
  };

  const styles = StyleSheet.create({
    button: {
      ...smallRoundedBlue
    },

    text: {
      fontSize: 16,
      lineHeight: 21,
      fontWeight: 'bold',
      letterSpacing: 0.25,
      color: 'white',
    },
  });