import React from 'react'
import { FaArrowRight } from 'react-icons/fa'

interface Props {
  roomId: string,
  userAmount: number,
  userLimit: number,
  roundId: string,
  roundLimit: number,
  onRoomJoin: (roomId: string) => any,
}

const RoomTile: React.FC<Props> = (room) => {
  return (
    <div
      className={`room-tile`}
      onDoubleClick={() => room.onRoomJoin(room.roomId)}
    >
      <h3>
        {room.roomId}{' '}
        <FaArrowRight
          style={{ color: 'green', cursor: 'pointer' }}
          onClick={() => room.onRoomJoin(room.roomId)}
        />
      </h3>
      <span>Users: {room.userAmount} </span>
      <span>Users limit: {room.userLimit} </span>
      <span>Round limit: {room.roundLimit} </span>
      <span>Round: {room.roundId}</span>
    </div>
  )
}

export default RoomTile
