package be.kdg.banditgames.platform.adapter.in;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.adapter.in.requests.friendship.FriendRequestDto;
import be.kdg.banditgames.platform.adapter.in.requests.friendship.RemoveFriendDto;
import be.kdg.banditgames.platform.adapter.in.response.FriendshipDto;
import be.kdg.banditgames.platform.adapter.in.response.FriendshipDtoMapper;
import be.kdg.banditgames.platform.adapter.in.response.PlayerDtoWithName;
import be.kdg.banditgames.platform.domain.Friendship;
import be.kdg.banditgames.platform.domain.Player;
import be.kdg.banditgames.platform.port.in.friendship.FindFriendshipPort;
import be.kdg.banditgames.platform.port.in.friendship.ManagingFriendshipUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/friendships")
public class FriendshipController {

    private final ManagingFriendshipUseCase managingFriendshipUseCase;
    private final FindFriendshipPort findFriendshipPort;

    public FriendshipController(ManagingFriendshipUseCase managingFriendshipUseCase,
                                FindFriendshipPort findFriendshipPort) {
        this.managingFriendshipUseCase = managingFriendshipUseCase;
        this.findFriendshipPort = findFriendshipPort;
    }
    
    @PostMapping("/request")
    public ResponseEntity<FriendshipDto> sendFriendRequest(@RequestBody FriendRequestDto request) {
        Friendship friendship = managingFriendshipUseCase.sendFriendRequest(
                new PlayerId(request.fromPlayerId()),
                new PlayerId(request.toPlayerId())
        );
        
        FriendshipDto friendshipDto = FriendshipDtoMapper.toDto(friendship);
        return ResponseEntity.ok(friendshipDto);
    }

    @PostMapping("/accept")
    public ResponseEntity<Void> acceptFriendRequest(@RequestBody FriendRequestDto request) {
        managingFriendshipUseCase.acceptFriendRequest(
                new PlayerId(request.fromPlayerId()),
                new PlayerId(request.toPlayerId())
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reject")
    public ResponseEntity<Void> rejectFriendRequest(@RequestBody FriendRequestDto request) {
        managingFriendshipUseCase.rejectFriendRequest(
                new PlayerId(request.fromPlayerId()),
                new PlayerId(request.toPlayerId())
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeFriend(@RequestBody RemoveFriendDto request) {
        managingFriendshipUseCase.removeFriend(
                new PlayerId(request.playerAId()),
                new PlayerId(request.playerBId())
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/friends/{playerId}")
    public ResponseEntity<List<PlayerDtoWithName>> getFriends(@PathVariable UUID playerId) {
        List<Player> friends = findFriendshipPort.getFriends(new PlayerId(playerId));
        List<PlayerDtoWithName> playerDtoWithName = friends.stream()
                .map(player -> new PlayerDtoWithName(
                        player.getPlayerId().playerId(),
                        player.getUsername()
                ))
                .toList();
                
        return ResponseEntity.ok(playerDtoWithName);
    }

    @GetMapping("/pending/{playerId}")
    public ResponseEntity<List<PlayerDtoWithName>> getPendingRequests(@PathVariable UUID playerId) {
        List<Player> pending = findFriendshipPort.getPendingRequests(new PlayerId(playerId));
        List<PlayerDtoWithName> playerDtoWithName = pending.stream()
                .map(player -> new PlayerDtoWithName(
                        player.getPlayerId().playerId(),
                        player.getUsername()
                ))
                .toList();

        return ResponseEntity.ok(playerDtoWithName);
    }

    @GetMapping("/between")
    public ResponseEntity<Optional<FriendshipDto>> getFriendshipBetween(
            @RequestParam UUID playerAId,
            @RequestParam UUID playerBId
    ) {
        Optional<Friendship> friendship = findFriendshipPort.getFriendshipBetween(
                new PlayerId(playerAId),
                new PlayerId(playerBId)
        );
        
        Optional<FriendshipDto> friendshipDto = friendship.map(FriendshipDtoMapper::toDto);
        return ResponseEntity.ok(friendshipDto);
    }


    @GetMapping("/sent/{playerId}")
    public ResponseEntity<List<PlayerDtoWithName>> getSentRequests(@PathVariable UUID playerId) {
        List<Player> sent = findFriendshipPort.getSentRequests(new PlayerId(playerId));
        List<PlayerDtoWithName> playerDtoWithName = sent.stream()
                .map(player -> new PlayerDtoWithName(
                        player.getPlayerId().playerId(),
                        player.getUsername()
                ))
                .toList();

        return ResponseEntity.ok(playerDtoWithName);
    }
}
