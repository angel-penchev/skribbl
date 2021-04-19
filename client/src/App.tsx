import React, { useState } from "react";
import RoomSettings from "./components/RoomSettings";
import UsernameInput from "./components/UsernameInput";

function App() {
  const [username, setUsername] = useState('')

  const onUsernameChange = (username: string) => {
    setUsername(username)
  }

  const onRoomCreate = () => {
    console.log('yay');
  }

  return (
    <div className="App">
      <div className='container'>
        <UsernameInput onUsernameChange={onUsernameChange} />
      </div>
      <div className='container'>
        <RoomSettings onRoomCreate={onRoomCreate} />
      </div>
    </div>
  );
}

export default App;
