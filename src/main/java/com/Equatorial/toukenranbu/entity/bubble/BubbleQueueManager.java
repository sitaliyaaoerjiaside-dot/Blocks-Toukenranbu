package com.Equatorial.toukenranbu.entity.bubble;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.*;

public class BubbleQueueManager {

    private static final int BUBBLE_DURATION = 40; // 2 秒

    // 整理的颜文字
    private static final String[] EMOTICONS = {
            "> ⤙ < ྀི", "(*^▽^*)", "(ಥ﹏ಥ)", "ヾ(✿ﾟ▽ﾟ)ノ", "༼つ◕_◕༽つ", "\\(^o^)/~",
            "ᕙ(  •̀ ᗜ •́  )ᕗ", "(●´З｀●)", "(*╯3╰)", "ヾ(*Ő౪Ő*)", "[○･｀Д´･ ○]", "(⊙︿⊙)",
            "(°ー°〃)", "Ծ‸Ծ", "(#`O′)", " (^o^)丿", "ლ(′◉❥◉｀ლ)", "(ง •_•)ง",
            "(*￣rǒ￣)", "（ﾉㄟ´Δ｀。)", "(ㅅ˘ㅂ˘)", "o(*////▽////*)q", "(◎_◎;)", "（●＾o＾●）",
            "(￣^￣)", "(￣ω￣;)", "（ΩДΩ）", "(`A´)", "(。-ω-)zzz", "ヽ(*≧ω≦)ﾉ", "( ´ ▽ ` )ﾉ"
    };

    private static final Map<ResourceKey<Level>, State> STATES = new HashMap<>();

    private static State state(ServerLevel level) {
        return STATES.computeIfAbsent(level.dimension(), k -> new State());
    }

    public static void enqueue(ServerLevel level, ToukenDanshiEntity danshi, String key, int sentenceCount) {
        if (key == null || key.isEmpty()) return;
        if (danshi.isSilenced()) return;

        // 如果无主，不管传入什么，强制替换为颜文字
        if (!danshi.isTame()) {
            key = EMOTICONS[danshi.getRandom().nextInt(EMOTICONS.length)];
            sentenceCount = 1;
        }

        State s = state(level);
        UUID id = danshi.getUUID();
        if (s.inQueue.contains(id)) return;
        if (id.equals(s.currentSpeaker)) return;

        int variant = sentenceCount <= 1 ? 0 : danshi.getRandom().nextInt(sentenceCount);
        PendingBubble bubble = new PendingBubble(id, key, variant);

        // 核心改动：有主的进 VIP 队列，野生的进普通队列
        if (danshi.isTame()) {
            s.tameQueue.add(bubble);
        } else {
            s.wildQueue.add(bubble);
        }
        s.inQueue.add(id);
    }

    // 专门用来发纯颜文字的方法
    public static void enqueueEmoticon(ServerLevel level, ToukenDanshiEntity danshi) {
        if (danshi.isSilenced()) return;
        String emoticon = EMOTICONS[danshi.getRandom().nextInt(EMOTICONS.length)];

        State s = state(level);
        UUID id = danshi.getUUID();
        if (s.inQueue.contains(id)) return;
        if (id.equals(s.currentSpeaker)) return;

        PendingBubble bubble = new PendingBubble(id, emoticon, 0);

        // 有主的进 VIP 队列，野生的进普通队列
        if (danshi.isTame()) {
            s.tameQueue.add(bubble);
        } else {
            s.wildQueue.add(bubble);
        }
        s.inQueue.add(id);
    }

    public static void tick(ServerLevel level) {
        State s = state(level);
        long now = level.getGameTime();

        if (s.currentSpeaker != null) {
            if (now < s.currentUntil) return;
            Entity e = level.getEntity(s.currentSpeaker);
            if (e instanceof ToukenDanshiEntity danshi) danshi.clearBubble();
            s.currentSpeaker = null;
        }

        // 优先处理有主刀男的队列
        while (!s.tameQueue.isEmpty() || !s.wildQueue.isEmpty()) {
            PendingBubble next = s.tameQueue.poll();
            if (next == null) {
                next = s.wildQueue.poll(); // 有主的都发完了，才轮到野生的
            }
            if (next == null) break;

            s.inQueue.remove(next.speakerId);
            Entity e = level.getEntity(next.speakerId);
            if (!(e instanceof ToukenDanshiEntity danshi)) continue;
            if (!danshi.isAlive()) continue;

            danshi.showBubble(next.key, next.variant);
            s.currentSpeaker = next.speakerId;
            s.currentUntil = now + BUBBLE_DURATION;
            break;
        }
    }

    private record PendingBubble(UUID speakerId, String key, int variant) {}

    private static class State {
        // 拆分成两个队列
        final Queue<PendingBubble> tameQueue = new LinkedList<>(); // 有主专属通道
        final Queue<PendingBubble> wildQueue = new LinkedList<>(); // 野生普通通道
        final Set<UUID> inQueue = new HashSet<>();
        UUID currentSpeaker = null;
        long currentUntil = 0L;
    }
}