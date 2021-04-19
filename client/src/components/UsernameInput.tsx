import { useState } from "react";

interface Props {
    onUsernameChange: (username: string) => any,
}

const UsernameInput: React.FC<Props> = ({onUsernameChange}) => {
    const [username, setUsername] = useState('')

    return (
        <div className='form-control'>
            <label>Username</label>
            <input
                type='text'
                placeholder='Ex.: Gosho'
                value={username}
                onChange={(e) => {
                    setUsername(e.target.value)
                    onUsernameChange(username)
                }}
            />
        </div>
    )
}

export default UsernameInput
