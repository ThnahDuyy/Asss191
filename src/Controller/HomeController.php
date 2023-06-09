<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\ItemsRepository;

class HomeController extends AbstractController
{
    #[Route('/', name: 'home')]
    public function index(ItemsRepository $itemsRepository): Response
    {
        return $this->render('home/index.html.twig', [
            'items' => $itemsRepository->findAll(),
        ]);
    }
}
