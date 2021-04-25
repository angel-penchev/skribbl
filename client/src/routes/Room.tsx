import React from 'react'
import { RouteComponentProps } from 'react-router-dom'
import Board from '../components/Board'
import Chat from '../components/Chat'

const Room: React.FC<RouteComponentProps> = ({ location }) => {
    const roomId = location.pathname.split("/").pop()
    return (
        <div>
            <div className="container">
                <span>Room ID: </span>
                <span>{roomId}</span>
            </div>
            <div className="container container-wide">
                <Board roomId={roomId ?? ''} />
                <Chat />
            </div>
        </div>
    )
}

export default Room
