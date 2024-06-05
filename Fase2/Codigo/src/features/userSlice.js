import { createSlice,createAsyncThunk } from '@reduxjs/toolkit';

// Here we use a Thunk to fetch data from an API. Thunks are used to handle async operations in Redux.
export const fetchUserData = createAsyncThunk('app/fetchUserData', async () => {
  try{
    const response = await fetch('https://39b6-193-137-92-72.ngrok-free.app/user');
    return response.json();
  } catch (error) {
    console.error('Error fetching User\'s data: ', error);
  }
});

// Here we define the slice for the app state.
// We use reducers to define actions that modify the state. It works like an event/action listener.

const initialState = {
  user: {
    username: "",
    first_name: "",
    last_name: "",
  },
  status: "idle",
  error: null,
}


const userSlice = createSlice({
  name: 'user',
  initialState: initialState,
  reducers: {
    // Here we can define additional actions to modify the state.
  },
  // Extra reducers are used here to handle the async actions, such as API calls.
  extraReducers: (builder) => {
    builder
      .addCase(fetchUserData.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(fetchUserData.fulfilled, (state, action) => {
        data = action.payload[0];

        console.log(data);

        state.status = 'succeeded';
        state.user.username = data.username;
        state.user.first_name = data.first_name;
        state.user.last_name = data.last_name;
        
        state.error = null;
      })
      .addCase(fetchUserData.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error;
      });
  },
});

export default userSlice.reducer;