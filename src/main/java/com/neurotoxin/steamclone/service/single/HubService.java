package com.neurotoxin.steamclone.service.single;

import com.neurotoxin.steamclone.entity.single.Game;
import com.neurotoxin.steamclone.entity.single.Hub;
import com.neurotoxin.steamclone.repository.single.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HubService {

    private final HubRepository hubRepository;

    @Transactional
    public Hub create(Hub hub) {
        return hubRepository.save(hub);
    }

    public Hub findHubById(Long hubId) {
        return hubRepository.findHubById(hubId);
    }

    public List<Hub> findAllHubs() {
        return hubRepository.findAll();
    }

    @Transactional
    public void delete(Hub hub) {
        hubRepository.delete(hub);
    }

    @Transactional
    public void disconnect(Game game) {
        Hub findHub = game.getHub();
        findHub.getCommunities().clear();
        game.setHub(null);
        hubRepository.delete(findHub);
    }
}
