import React, { useState } from "react";
import AppBar from "@material-ui/core/AppBar";
import Tab from "@material-ui/core/Tab";
import Tabs from "@material-ui/core/Tabs";
import TabPanel from "../components/TabPanel";
import RoomSettings from "../components/RoomSettings";
import UsernameInput from "../components/UsernameInput";
import { RouteComponentProps } from "react-router-dom";
import JoinRoom from "../components/JoinRoom";

const Home: React.FC<RouteComponentProps> = () => {
    const [username, setUsername] = useState('')
    const [value, setValue] = useState(0);

    const onUsernameChange = (username: string) => {
        setUsername(username)
    }

    const onRoomCreate = () => {
        if (!username) {
            alert('Please, enter a username.')
            return
        }
    }

    const onRoomJoin = (roomId: string) => {
        if (!username) {
            alert('Please, enter a username.')
            return
        }
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
              <JoinRoom onRoomJoin={onRoomJoin}/>
            </TabPanel>
            <TabPanel value={value} index={2}>
              <p>Public Rooms</p>
            </TabPanel>
          </div>
        </div>
      );
}

export default Home
