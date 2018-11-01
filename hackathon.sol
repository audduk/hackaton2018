pragma solidity ^0.4.0;
contract FileTrasfer {

    struct FileInfo {
        bool exists;
        address sender;
        uint256 sendTime;
    }

    /// Отображение хеша исходного файла на получателя файла
    /// Регистрация факта отправки (предоставления) файла
    mapping(bytes32 => FileInfo) files;

    /// Фиксация факта отправки файла
    function registerFile(bytes32 hash) public {
        require(!files[hash].exists);
        FileInfo storage info = files[hash];
        info.exists = true;
        info.sender = msg.sender;
        info.sendTime = now;
    }

    struct TransferInfo {
        bool registered;
        bool received;
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
        log2(hash, encryptedHash, password);
        require(files[hash].exists);
        require(!transfers[encryptedHash].registered);
        TransferInfo storage info = transfers[encryptedHash];
        info.registered = true;
        info.hash = hash;
        info.encryptedHash = encryptedHash;
        info.password = password;
        info.transferTime = now;
    }

    /// Запрос пароля, выданного для заданного зашифрованного файла
    /// Вызывает получатель файла после расчета хеша полученного зашифрованного файла
    function approveReceiving(bytes32 encryptedHash) public {
        TransferInfo storage info = transfers[encryptedHash];
        log1(encryptedHash, info.encryptedHash);
        require(info.registered);
        require(!info.received);
        require(info.encryptedHash == encryptedHash);
        info.received = true;
        info.receiver = msg.sender;
        info.receiveTime = now;
    }

    function ping(uint p1, uint p2) public pure returns (uint) {
        return p1 + p2;
    }

    function getResponse(bytes32 encryptedHash) public returns (bytes32, address, bytes32) {
        require(transfers[encryptedHash].received);
        TransferInfo memory info = transfers[encryptedHash];
        log1(info.hash, info.password);
        return (info.hash, info.receiver, info.password);
    }
}
