import { createSlice,createAsyncThunk } from '@reduxjs/toolkit';

export const login = createAsyncThunk('app/login', async ({ user, pass }) => {
  const body = {
      'username': user, 
      'email': "",
      'password': pass,
  }

  try{
    const response = await fetch('https://39b6-193-137-92-72.ngrok-free.app/login',{
      credentials: 'omit',
      method: "POST",
      headers:{
          'Content-Type': 'application/json',
      },
      body: JSON.stringify(body)
  });
    return response.headers.get('Set-Cookie');
  } catch (error) {
    console.error('Error fetching login\'s data: ', error);
  }
});

const initialState = {
  user: {
    username: "",
    first_name: "",
    last_name: "",
  },

  cookies:{
    csrftoken: "",
    sessionid: "",
  },

  preferences: {
    notifications: true,
    notify_distance: 500,
  },

  isPremium: false,

  trail_running: -1,

  status: "idle",
  error: null,
}


const userSlice = createSlice({
  name: 'user',
  initialState: initialState,
  reducers: {
    putUser: (state, action) => {
      data = action.payload

      state.user.username = data.username
      state.user.first_name = data.first_name
      state.user.last_name = data.last_name
      state.isPremium = data.user_type === 'Premium'
    },

    updatePreferences: (state, action) => {
      state.preferences.notifications = action.payload.notifications;
      state.preferences.notify_distance = action.payload.distance;
    },

    updateTrailRunning: (state, action) => {
      state.trail_running = action.payload;
    },

    resetState: (state) => {
      state = initialState;
    }
  },
  // Extra reducers are used here to handle the async actions, such as API calls.
  extraReducers: (builder) => {
    builder
      // Login Case
      .addCase(login.pending, (state) => {
        state.isLoggedIn = false;
      })
      .addCase(login.fulfilled, (state, action) => {
        const responseHeaders = action.payload;
        const csrfTokenMatch = responseHeaders.match(/csrftoken=([^;]+)/);
        const csrfToken = csrfTokenMatch ? csrfTokenMatch[1] : null;

        const sessionIdMatch = responseHeaders.match(/sessionid=([^;]+)/);
        const sessionId = sessionIdMatch ? sessionIdMatch[1] : null;

        state.cookies.csrftoken = csrfToken;
        state.cookies.sessionid = sessionId;

        state.status = 'succeeded';
        state.error = null;
      })
      .addCase(login.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error;
      })
  },
});

export const { putUser, updatePreferences, updateTrailRunning, resetState } = userSlice.actions;
export default userSlice.reducer;