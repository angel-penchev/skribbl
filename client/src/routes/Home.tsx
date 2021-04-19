import React, { useState } from "react";
import AppBar from "@material-ui/core/AppBar";
import Tab from "@material-ui/core/Tab";
import Tabs from "@material-ui/core/Tabs";
import TabPanel from "../components/TabPanel";
import CreateRoom from "../components/CreateRoom";
import UsernameInput from "../components/UsernameInput";
import { RouteComponentProps, useHistory } from "react-router-dom";
import JoinRoom from "../components/JoinRoom";

const Home: React.FC<RouteComponentProps> = () => {
    const [username, setUsername] = useState('')
    const [value, setValue] = useState(0);
    let history = useHistory();

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

        history.push(roomId)
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
              <CreateRoom onRoomCreate={onRoomCreate} />
            </TabPanel>
            <TabPanel value={value} index={1}>
              <JoinRoom onRoomJoin={onRoomJoin}/>
            </TabPanel>
            <TabPanel value={value} index={2}>
              Public Rooms
            </TabPanel>
          </div>
        </div>
      );
}

export default Home
