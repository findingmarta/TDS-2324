import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigation } from '@react-navigation/native';
import { resetState } from '../features/userSlice';

const LogoutButton = () => {
  const dispatch = useDispatch();
  const navigation = useNavigation();

  useEffect(() => {
    // Clear cookies and application state
    dispatch(resetState());

    // Navigate to login screen
    navigation.reset({
      index: 0,
      routes: [{ name: 'login' }],
    });
  }, [dispatch, navigation]);

  return null;
};

export default LogoutButton;