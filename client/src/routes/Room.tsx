import React from 'react'
import { RouteComponentProps } from 'react-router-dom'
import Board from '../components/Board'
import Chat from '../components/Chat'

const Room: React.FC<RouteComponentProps> = ({location}) => {
    const roomId = location.pathname.split("/").pop()

    // @ts-ignore
    const username = location.state ? location.state.username : prompt("Please, enter a username", "Gosho");

    return (
        <div>
            <div className="container">
                <span><b>Room ID: </b>z</span>
                <span>{roomId}</span>
            </div>
            <div className="container container-wide">
                <Board roomId={roomId ?? ''} />
                <Chat roomId={roomId ?? ''} username={username} />
            </div>
        </div>
    )
}

export default Room
