import React, { FormEvent, useState } from 'react'

interface Props {
    onRoomJoin: (roomId: string) => any; 
}

const JoinRoom: React.FC<Props> = ({onRoomJoin}) => {
    const [roomId, setRoomId] = useState('')

    const onSubmit = (e: FormEvent<Element>) => {
        e.preventDefault()

        if (!roomId) {
            alert('Please, enter a Room ID.')
            return
        }

        onRoomJoin(roomId)
    }

    return (
        <form className='room-form' onSubmit={onSubmit}>
            <div className='form-control'>
                <label>Room ID</label>
                <input
                    type='text'
                    placeholder='Ex.: c6d125f1-5499-43e6-8b10-24a5a7da2e0a'
                    value={roomId}
                    onChange={(e) => setRoomId(e.target.value)}
                />
            </div>
            <input type='submit' value='Join Room' className='btn btn-block' />
        </form>
    )
}

export default JoinRoom
