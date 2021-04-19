import AppBar from "@material-ui/core/AppBar";
import Tab from "@material-ui/core/Tab";
import Tabs from "@material-ui/core/Tabs";
import React, { useState } from "react";
import RoomSettings from "./components/RoomSettings";
import TabPanel from "./components/TabPanel";
import UsernameInput from "./components/UsernameInput";

function App() {
  const [username, setUsername] = useState('')
  const [value, setValue] = React.useState(0);

  const onUsernameChange = (username: string) => {
    setUsername(username)
  }

  const onRoomCreate = () => {
    console.log('yay');
  }

  const handleChange = (event: React.ChangeEvent<{}>, newValue: any) => {
    setValue(newValue);
  };

  return (
    <div className="App">
      <div className='container'>
        <UsernameInput onUsernameChange={onUsernameChange} />
        <AppBar position="static">
          <Tabs value={value} onChange={handleChange}>
            <Tab label="Create Room" />
            <Tab label="Join Room" />
            <Tab label="Public Rooms" />
          </Tabs>
        </AppBar>
        <TabPanel value={value} index={0}>
          <RoomSettings onRoomCreate={onRoomCreate} />
        </TabPanel>
        <TabPanel value={value} index={1}>
          <p>Join Room</p>
        </TabPanel>
        <TabPanel value={value} index={2}>
          <p>Public Rooms</p>
        </TabPanel>
      </div>
    </div>
  );
}

export default App;
