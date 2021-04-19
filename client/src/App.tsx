import React from "react";
import RoomSettings from "./components/RoomSettings";

function App() {
  const onRoomCreate = () => {
    console.log('yay');
  }

  return (
    <div className="App">
      <div className='container'>
        <RoomSettings onRoomCreate={onRoomCreate} />
      </div>
    </div>
  );
}

export default App;
