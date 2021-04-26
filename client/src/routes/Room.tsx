import React, { useState } from 'react'
import SockJsClient from 'react-stomp'
import { RouteComponentProps } from 'react-router-dom'
import Board from '../components/Board'
import Chat from '../components/Chat'

const Room: React.FC<RouteComponentProps> = ({location}) => {
    const [socketClient, setSocketClient] = useState()
    const roomId = location.pathname.split("/").pop()

    // @ts-ignore
    const username = location.state ? location.state.username : prompt("Please, enter a username", "Gosho");

    return (
        <div>
            <div className="container">
                <span><b>Room ID: </b></span>
                <span>{roomId}</span>
            </div>
            <div className="container container-wide">
                <Board roomId={roomId ?? ''} />
                <Chat roomId={roomId ?? ''} username={username} />
            </div>
            <SockJsClient url='http://localhost:8080/skribbl/'
                topics={[`/topic/room/${roomId}/game`]}
                onConnect={() => {
                    console.log("Connected")
                    // @ts-ignore
                    if (socketClient) socketClient.sendMessage(`/app/room/${roomId}/game`, JSON.stringify({
                        type: 'connection',
                        message: username
                    }));
                }}
                onDisconnect={() => {
                    console.log("Disconnected");
                }}
                onMessage={(msg: any) => {
                    console.log(msg);
                }}
                ref={(socketClient: any) => {
                    setSocketClient(socketClient)
                }} />
        </div>
    )
}

export default Room
