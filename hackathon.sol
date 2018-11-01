pragma solidity ^0.4.0;
contract FileTrasfer {

    struct FileInfo {
        address sender;
        uint256 sendTime;
    }

    /// Отображение хеша исходного файла на получателя файла
    /// Регистрация факта отправки (предоставления) файла
    mapping(bytes32 => FileInfo) files;

    /// Фиксация факта отправки файла
    function registerFile(bytes32 hash) public {
        FileInfo storage info = files[hash];
        //if (files[hash]) return;
        info.sender = msg.sender;
        info.sendTime = now;
        files[hash] = info;
    }

    struct TransferInfo {
        bytes32 hash;
        bytes32 encryptedHash;
        bytes32 password;
        uint256 transferTime;
        address receiver;
        uint256 receiveTime;
    }

    /// Отображение encryptedHash в информацию об обмене
    mapping(bytes32 => TransferInfo) transfers;

    /// Файл (зашифрованный) передан получателю
    /// Вызывает отправитель файла после факта отправки
    function registerResponse(bytes32 hash, bytes32 encryptedHash, bytes32 password) public {
        TransferInfo storage info = transfers[encryptedHash];
        info.hash = hash;
        info.encryptedHash = encryptedHash;
        info.password = password;
        info.transferTime = now;
        transfers[encryptedHash] = info;
    }

    /// Запрос пароля, выданного для заданного зашифрованного файла
    /// Вызывает получатель файла после расчета хеша полученного зашифрованного файла
    function approveReceiving(bytes32 encryptedHash) public returns (bytes32) {
        //if (!transfers[encryptedHash]) return 0x0;
        TransferInfo storage info = transfers[encryptedHash];
        info.receiver = msg.sender;
        info.receiveTime = now;
        return info.password;
    }

    function ping(uint p1, uint p2) public pure returns (uint) {
        return p1 + p2;
    }

    function getResponse(bytes32 encryptedHash) public view returns (bytes32, address, bytes32) {
        //if (transfers[encryptedHash].receiver != msg.sender) return (0x0, 0x0, 0x0);
        TransferInfo storage info = transfers[encryptedHash];
        return (info.hash, info.receiver, info.password);
    }
}
