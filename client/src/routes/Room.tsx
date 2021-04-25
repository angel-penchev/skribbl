import React from 'react'
import { RouteComponentProps } from 'react-router-dom'
import Board from '../components/Board'

const Room: React.FC<RouteComponentProps> = ({ location }) => {
    return (
        <div>
            <div className="container">
                <span>Room ID: </span>
                <span>{location.pathname.split("/").pop()}</span>
            </div>
            <div className="container container-wide">
                <Board />
            </div>
        </div>
    )
}

export default Room
