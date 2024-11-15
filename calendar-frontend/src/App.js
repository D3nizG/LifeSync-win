import React, {useEffect} from 'react';
import { gapi } from 'gapi-script';
import { useNavigate } from 'react-router-dom';
import './index.css';
import './App.css';

function App() {
  const navigate = useNavigate();

  useEffect(() => {
    const initClient = () => {
      gapi.client.init({
        clientId: 'CLIENT_ID_HERE',
        scope: 'https://www.googleapis.com/auth/calendar',
      });
    };
    gapi.load('client:auth2', initClient);
  }, []);

  const handleLogin = async () => {
    const GoogleAuth = gapi.auth2.getAuthInstance();
    let calendarId = null;
    try {
      const response = await GoogleAuth.signIn();
      const accessToken = response.getAuthResponse().access_token;
      console.log('Access token:', accessToken);

      const apiResponse = await fetch('http://localhost:8080/api/v1/event/auth', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${accessToken}`
        }
      });

      const calendarResponse = await fetch('https://www.googleapis.com/calendar/v3/users/me/calendarList', {
        headers: {
          'Authorization': `Bearer ${accessToken}`
        }
      });

      const calendarData = await calendarResponse.json();
      calendarData.items.forEach((item) => {
        if (item.primary) {
          calendarId = item.id;
        }
      })

      navigate('/calendar', { state: { calendarId, accessToken } });
    } catch (error) {
      console.error('Error fetching calendar:', error);
    }
  };

  return (
      <div id="login">
        <h1>LifeSync</h1>
        <button id="loginButton" onClick={handleLogin}>Login with Google</button>
      </div>
  );
}

export default App;