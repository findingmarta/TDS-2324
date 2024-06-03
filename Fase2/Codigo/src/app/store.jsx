import {combineReducers, configureStore} from '@reduxjs/toolkit';
import appSlice from '../features/appSlice';
//import userSlice from '../features/userSlice';
import trailsSlice from '../features/trailsSlice';
//import historySlice from '../features/historySlice';



// A "slice" is a collection of Redux reducer logic and actions for a single feature in your app, 
// typically defined together in a single file. 
// The name comes from splitting up the root Redux state object into multiple "slices" of state.
const rootReducer = combineReducers({
  app: appSlice,
  trails:trailsSlice
})

// Here we define the store, which is the global state of the Redux application.
// The store setup is wrapped in `makeStore` to allow reuse
// when setting up tests that need the same store config
export default configureStore({
  reducer:  rootReducer
});
