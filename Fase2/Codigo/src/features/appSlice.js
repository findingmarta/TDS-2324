import { createSlice,createAsyncThunk } from '@reduxjs/toolkit';

// Here we use a Thunk to fetch data from an API. Thunks are used to handle async operations in Redux.
export const fetchAppData = createAsyncThunk('app/fetchAppData', async () => {
  try{
    console.log('Fetching App\'s data...');
    const response = await fetch('https://39b6-193-137-92-72.ngrok-free.app/app');
    return response.json();
  } catch (error) {
    console.error('Error fetching App\'s data: ', error);
  }
});

// Here we define the slice for the app state.
// We use reducers to define actions that modify the state. It works like an event/action listener.

const initialState = {
  app: {
    appName: '',
    appDescription: '',
    appLandingPage: '',
    socials: [],
    contacts: [],
    partners: []
  },
  status: "idle",
  error: null,
}


const appSlice = createSlice({
  name: 'app',
  initialState: initialState,
  reducers: {
    // Here we can define additional actions to modify the state.
  },
  // Extra reducers are used here to handle the async actions, such as API calls.
  extraReducers: (builder) => {
    builder
      .addCase(fetchAppData.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(fetchAppData.fulfilled, (state, action) => {
        data = action.payload[0];

        state.status = 'succeeded';
        state.app.appName = data.app_name;
        state.app.appDescription = data.app_desc;
        state.app.appLandingPage = data.app_landing_page_text;
        state.app.socials = data.socials;
        state.app.contacts = data.contacts;
        state.app.partners = data.partners;
        
        state.error = null;
      })
      .addCase(fetchAppData.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error;
      });
  },
});

export default appSlice.reducer;