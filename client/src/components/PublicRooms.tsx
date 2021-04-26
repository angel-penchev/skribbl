import React, { useEffect, useState } from 'react'
import RoomTile from './RoomTile'

interface Props {
    onRoomJoin: (roomId: string) => any;
}

const PublicRooms: React.FC<Props> = ({ onRoomJoin }) => {
    const [rooms, setRooms] = useState([])
    useEffect(() => {
        const getRooms = async () => {
            const res = await fetch('http://localhost:8080/api/public-rooms')
            const roomsFromServer = await res.json()
            setRooms(roomsFromServer)
        }

        getRooms()
    }, [])

    return (
        <div>
            {rooms.length > 0 ? (
                rooms.map((room: any, index: any) => (
                    <RoomTile
                        key={index}
                        roomId={room.roomId}
                        userAmount={room.userAmount}
                        userLimit={room.userLimit}
                        roundId={room.roundId}
                        roundLimit={room.roundLimit}
                        onRoomJoin={onRoomJoin} />
                ))
            ) : (
                    <span>No public rooms available!</span>
                )}
        </div>
    )
}

export default PublicRooms
