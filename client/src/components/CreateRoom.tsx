import React, { FormEvent, useState } from 'react'

interface RoomCreateParams {
  roundLimit: number,
  userLimit: number,
  timeToDraw: number,
  wordlist: string,
  customWords: string,
  isPublic: boolean
}

interface Props {
    onRoomCreate: (params: RoomCreateParams) => any;
}

const minRounds = 3
const maxRounds = 10
const minUsers = 3
const maxUsers = 10
const minTimeToDraw = 10
const maxTimeToDraw = 100
const wordlists = ['english', 'bulgarian', 'custom']

const CreateRoom: React.FC<Props> = ({ onRoomCreate }) => {
    const [roundLimit, setRoundLimit] = useState('3')
    const [userLimit, setUserLimit] = useState('5')
    const [timeToDraw, setTimeToDraw] = useState('69')
    const [wordlist, setWordlist] = useState(wordlists[0])
    const [customWords, setCustomWords] = useState('')
    const [isPublic, setIsPublic] = useState(false)

    const onSubmit = (e: FormEvent<Element>) => {
        e.preventDefault()
        onRoomCreate({
            roundLimit: parseInt(roundLimit),
            userLimit: parseInt(userLimit),
            timeToDraw: parseInt(timeToDraw),
            wordlist: wordlist,
            customWords: customWords,
            isPublic: isPublic,
        })
    }

    return (
        <form className='room-form' onSubmit={onSubmit}>
            <div className='form-control'>
                <label>Rounds</label>
                <input
                    type="number"
                    value={roundLimit}
                    onChange={(e) => setRoundLimit(e.target.value)}
                    min={minRounds}
                    max={maxRounds}
                />
            </div>
            <div className='form-control'>
                <label>Max Users</label>
                <input
                    type="number"
                    value={userLimit}
                    onChange={(e) => setUserLimit(e.target.value)}
                    min={minUsers}
                    max={maxUsers}
                />
            </div>
            <div className='form-control'>
                <label>Time to draw</label>
                <input
                    type="number"
                    value={timeToDraw}
                    onChange={(e) => setTimeToDraw(e.target.value)}
                    min={minTimeToDraw}
                    max={maxTimeToDraw}
                />
            </div>
            <div className='form-control'>
                <label>Wordlist</label>
                <select value={wordlist} onChange={(e) => setWordlist(e.target.value)}>
                    {
                        wordlists.map((value, index) => {
                            return <option key={index.toString()} value={value}>{value}</option>
                        })
                    }
                </select>
            </div>
            <div className='form-control'>
                <label>Custom words</label>
                <textarea
                    value={customWords}
                    onChange={(e) => setCustomWords(e.target.value)}
                    disabled={wordlist !== 'custom'}
                ></textarea>
            </div>
            <div className='form-control'>
                <label>Set Public</label>
                <input
                    type='checkbox'
                    checked={isPublic}
                    onChange={(e) => setIsPublic(e.currentTarget.checked)}
                />
            </div>
            <input type='submit' value='Create Room' className='btn btn-block' />
        </form>
    )
}

export default CreateRoom
