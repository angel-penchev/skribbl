import React, { FormEvent, useState } from 'react'

interface Props {
    onRoomCreate: () => any;
}

const minRounds = 3
const maxRounds = 10
const minUsers = 3
const maxUsers = 10
const wordlists = ['english', 'bulgarian', 'custom']

const RoomSettings: React.FC<Props> = ({onRoomCreate}) => {
    const [rounds, setRounds] = useState('3')
    const [users, setUsers] = useState('5')
    const [wordlist, setWordlist] = useState(wordlists[0])
    const [customWords, setCustomWords] = useState('')

    const onSubmit = (e: FormEvent<Element>) => {
        e.preventDefault()
    
        onRoomCreate()
    }

    return (
        <form className='room-form' onSubmit={onSubmit}>
            <div className='formControl'>
                <label>Rounds</label>
                <input
                    type="number"
                    value={rounds}
                    onChange={(e) => setRounds(e.target.value)}
                    min={minRounds}
                    max={maxRounds}
                />
            </div>
            <div className='formControl'>
                <label>Max Users</label>
                <input
                    type="number"
                    value={users}
                    onChange={(e) => setUsers(e.target.value)}
                    min={minUsers}
                    max={maxUsers}
                />
            </div>
            <div className='formControl'>
                <label>Wordlist</label>
                <select value={wordlist} onChange={(e) => setWordlist(e.target.value)}>
                    {
                        wordlists.map((value, index) => {
                            return <option id={index.toString()} value={value}>{value}</option>
                        })
                    }
                </select>
            </div>
            <div className='formControl'>
                <label>Custom words</label>
                <textarea
                    value={customWords}
                    onChange={(e) => setCustomWords(e.target.value)}
                ></textarea>
            </div>
            <input type='submit' value='Create Room' className='btn btn-block' />
        </form>
    )
}

export default RoomSettings